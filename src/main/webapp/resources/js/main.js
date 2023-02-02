import {Graph} from "./graph.js";
import {Validator} from "./validator.js";


$(document).ready(function () {
    const xButtons = $('#values-form\\:x')
    const xButton = $('#values-form\\:x_hidden');
    const yInput = $("#values-form\\:y-input");
    const yBlock = $("#y-block")
    const radioBlock = $("#r-checkbox");
    const rSelect = $('input[type="checkbox"]');
    const graphSVG = $('.svg-graph');

    const graph = new Graph({
        $graphSvg: graphSVG,
        $rHalfPos: $('.r-half-pos'),
        $rWholePos: $('.r-whole-pos'),
        $rHalfNeg: $('.r-half-neg'),
        $rWholeNeg: $('.r-whole-neg '),
        $centreX: 150,
        $centreY: 150,
    })

    const validator = new Validator(
        -3,
        3,
        -5,
        3,
        [1, 2, 3, 4, 5]
    )

    rSelect.change(() => {
        const countR = getCountRValue();
        const rValues = getArrayRValues();
        let r;
        if (countR === 1) {
            r = rValues[0];
        } else {
            graph.setDefaultValueR();
            graph.removeLines();
            return;
        }
        if (validator.validateR(r)) {
            validator.deleteErrorMessage(radioBlock);
            graph.setRValue(r);
        } else {
            graph.setDefaultValueR();
            graph.removeLines();
        }

    })


    yInput.keyup(() => {
        let y = getYValue();
        if (validator.validateY(y)) {
            validator.deleteErrorMessage(yBlock);
        }
    })

    xButton.click(function () {
        let x = getXValue();
        if (validator.validateX(x)) {
            validator.deleteErrorMessage(xButtons)
        }
    });

    graphSVG.mousemove(event => {
        let countRValue = getCountRValue();
        if (!validator.validateRValueFromGraph(countRValue) && !rSelect.siblings().hasClass("wrong-value")) {
            validator.deleteErrorMessage(radioBlock);
            validator.showErrorMessage(radioBlock, validator.CHOOSE_R_LABEL);
            return;
        }
        graph.setRawValueX(event.offsetX, xButton);
        graph.setRawValueY(event.offsetY, yInput);
    })

    graphSVG.mouseleave(() => {
        graph.resetRawValues();
        validator.deleteErrorMessage(radioBlock);
    })

    graphSVG.click(() => {
        const x = graph.rawValueX;
        const y = graph.rawValueY
        const r = graph.rValue;
        $('.graphX').val(x);
        $('.graphY').val(y);
        $('.graphR').val(r);
        if (!validator.validateAllInputValues(String(x), String(y), String(r), xButtons, yBlock, radioBlock, xButtons)) return;
        $('#hidden-graph-form\\:graph-submit-buttons').click();
    })


    $('#values-form\\:submit-button').click(() => {
        console.log("Отправляем запрос")
        const x = getXValue();
        const y = getYValue();
        const r = getCountRValue();
        if (!validator.validateAllInputValues(x, y, r, xButtons, yBlock, radioBlock, xButtons)) return;
        $('#values-form\\:submit-buttons').click();
    })

    $('input[type=reset]').click(() => {
        graph.resetValues();
        validator.deleteAllErrorMessage(xButtons, yBlock, radioBlock,)
    })

    function addClassHit(element) {
        let result = $(element).find("td:last").text();
        const classResult = result === 'true' ? 'hit' : 'miss';
        $(element).addClass(classResult);
    }

    function getXValue() {
        let x = $('#values-form\\:sliderValue').text();
        return x != null && x.length !== 0 ?
            x.replace(',', '.') :
            null;
    }

    function getRValue() {
        let r = rSelect.val();
        return r != null && r.length !== 0 ?
            r.replace(',', '.') :
            null;
    }

    function getYValue() {
        let y = yInput.val();
        return y != null && y.length !== 0 ?
            y.replace(',', '.') :
            null;
    }

    function getCountRValue() {
        return getArrayRValues().length;
    }

    function getArrayRValues() {
        let int = 1;
        let array = [];
        rSelect.map((index, element) => {
            if (element.checked) {
                array.push(int);
            }
            int++;
        })

        return array;
    }

    function drawDot(xValue, yValue, rValue, result, centreY, centreX) {
        if (xValue == null || yValue == null) return;
        const y = centreY - yValue * 100 / rValue;
        const x = centreX + xValue * 100 / rValue;

        const dot = $(document.createElementNS('http://www.w3.org/2000/svg', 'circle'));
        dot.attr({
            cx: x,
            cy: y,
            r: 3
        }).add('circle')
        dot.addClass(result)
        graphSVG.append(dot)
    }

    function redrawDot() {

        $('#result-table tr').each(function (row) {
            let list = []
            $(this).find('td').each(function (cell) {
                list.push($(this).html())
            });
            let x = list[0];
            let y = list[1];
            let r = list[2];
            let result = list[3];
            drawDot(x, y, r, result, 150, 150)

        })
    }

    redrawDot();

})
