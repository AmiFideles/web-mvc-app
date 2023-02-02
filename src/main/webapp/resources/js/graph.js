export class Graph {
    graphSVG;
    rHalfPos;
    rWholePos;
    rHalfNeg;
    rWholeNeg;
    xValue;
    yValue;
    rValue;
    rawValueX;
    rawValueY;
    centreX;
    centreY;
    xButton;

    constructor({$graphSvg, $rHalfPos, $rWholePos, $rHalfNeg, $rWholeNeg, $centreX, $centreY}) {
        this.graphSVG = $graphSvg;
        this.rHalfPos = $rHalfPos;
        this.rWholePos = $rWholePos;
        this.rHalfNeg = $rHalfNeg;
        this.rWholeNeg = $rWholeNeg;
        this.centreX = $centreX;
        this.centreY = $centreY;
        /*this.rValue = Number($('#r-options').val())*/
        this.rValue = null;
        this.xButton = [-3, -2.5, -2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2, 2.5, 3]
    }


    drawDot = () => {
        this.removeDot();
        if (this.xValue == null || this.yValue == null) return;
        const y = this.centreY - this.yValue * 100 / this.rValue;
        const x = this.centreX + this.xValue * 100 / this.rValue;

        const dot = $(document.createElementNS('http://www.w3.org/2000/svg', 'circle'));
        dot.attr({
            cx: x,
            cy: y,
            r: 2
        }).add('circle')
        dot.addClass("tempCircle")
        this.graphSVG.append(dot)

    }

    checkDot = (x, y) => {
        if (!(x >= 0 && x <= 300 && y >= 0 && y <= 300)) {
            return false;
        }
    }


    removeDot = () => {
        this.graphSVG.children('.tempCircle').remove();
    }


    removeXLines = () => {
        this.graphSVG.children('.dotted-line-x').remove();
    }

    removeYLines = () => {
        this.graphSVG.children('.dotted-line-y').remove();
    }

    removeLines = () => {
        this.removeXLines();
        this.removeYLines();
        this.removeDot();
    }

    removeRawXLines = () => {
        this.graphSVG.children('.dotted-raw-x').remove();
    }

    removeRawYLines = () => {
        this.graphSVG.children('.dotted-raw-y').remove();
    }

    removeRawLines = () => {
        this.graphSVG.children('.dotted-line-x').remove();
        this.graphSVG.children('.dotted-line-y').remove();
        this.rawValueY = null;
        this.rawValueX = null;
    }

    setDefaultValueR = () => {
        this.rHalfPos.text('R/2')
        this.rHalfNeg.text('-R/2')
        this.rWholePos.text('R')
        this.rWholeNeg.text('-R')
        this.rValue = null;
    }

    changeLabelR = () => {
        this.rHalfPos.text(this.rValue / 2);
        this.rWholePos.text(this.rValue);
        this.rHalfNeg.text(-this.rValue / 2);
        this.rWholeNeg.text(-this.rValue);
    }

    resetValues = () => {

        this.removeDot();
        this.removeYLines();
        this.removeXLines()
        this.setDefaultValueR();
        this.resetRawValues();
        this.xValue = null;
        this.yValue = null;
        this.rValue = null;
    }

    setXValue = (xValue) => {
        this.xValue = xValue;
        this.removeXLines();
        this.removeDot();
        if (this.rValue == null || xValue == null) return;
        const x = this.centreX + xValue * 100 / this.rValue;
        const lineX = $(document.createElementNS('http://www.w3.org/2000/svg', 'line'));
        lineX.attr({
            x1: x,
            x2: x,
            y1: 0,
            y2: 300,
        }).addClass('dotted-line-x');
        this.graphSVG.append(lineX)
        this.drawDot();
    }

    setYValue = (yValue) => {
        this.yValue = yValue;
        this.removeYLines();
        this.removeDot();
        if (this.rValue == null || yValue == null) return;

        const y = this.centreY - yValue * 100 / this.rValue;
        const lineY = $(document.createElementNS('http://www.w3.org/2000/svg', 'line'));
        lineY.attr({
            x1: 0,
            x2: 300,
            y1: y,
            y2: y,
        }).addClass('dotted-line-y')

        this.graphSVG.append(lineY)
        this.drawDot();

    }

    setRValue = (rValue) => {
        this.rValue = rValue;
        this.changeLabelR();
        this.setXValue(this.xValue);
        this.setYValue(this.yValue);
        this.drawDot();
    }

    resetRawValues = () => {
        this.removeRawXLines();
        this.removeRawYLines();
        this.rawValueY = null;
        this.rawValueX = null;
    }


    setRawValueX(rawValueX, xButton) {
        if (this.rValue == null || rawValueX == null) return;
        this.graphSVG.children('.dotted-raw-x').remove();
        const x = (rawValueX - this.centreX) * this.rValue / 100;
        /*this.xButton.each((index, element) => */
        $.each(this.xButton, (index, element) => {
            const value = Number(element);
            if (value - 0.25 < x && value + 0.25 >= x) {
                this.rawValueX = value;
                const line = $(document.createElementNS('http://www.w3.org/2000/svg', 'line'));
                line.attr({
                    x1: this.centreX + value * 100 / this.rValue,
                    x2: this.centreX + value * 100 / this.rValue,
                    y1: 0,
                    y2: 300,
                }).addClass('dotted-raw-x')
                this.graphSVG.append(line)
            }
        });
    }


    setRawValueY = (rawValueY, yInput) => {
        if (this.rValue == null || rawValueY == null) return;
        this.graphSVG.children('.dotted-raw-y').remove();
        this.rawValueY = (this.centreY - rawValueY) * this.rValue / 100;
        const line = $(document.createElementNS('http://www.w3.org/2000/svg', 'line'))
        line.attr({
            x1: 0,
            x2: 300,
            y1: rawValueY,
            y2: rawValueY,
        }).addClass('dotted-raw-y');

        this.graphSVG.append(line)

    }

}
