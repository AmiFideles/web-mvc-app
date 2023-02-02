const graphSVG = $('.svg-graph');

function drawDot(xValue, yValue, rValue, result, centreY, centreX) {
    if (xValue == null || yValue == null) return;
    const y = centreY - yValue * 100 / rValue;
    const x = centreX + xValue * 100 / rValue;

    if (!(isNaN(x) && isNaN(y))) {
        const dot = $(document.createElementNS('http://www.w3.org/2000/svg', 'circle'));
        dot.attr({
            cx: x,
            cy: y,
            r: 3
        }).add('circle')
        dot.addClass(result)
        graphSVG.append(dot)
    }
}

function redrawDot(data) {
    //  if (data.status==="success"){
    $('#result-table tr').each(function (row) {
        let list = []
        $(this).find('td').each(function (cell) {
            list.push($(this).html())
        });

        let x = Number(list[0]);
        let y = Number(list[1]);
        let r = Number(list[2]);
        let result = list[3];
        drawDot(x, y, r, result, 150, 150)


    })
    // }
}

function removeDot() {
    $("svg circle").remove();
}

let table = document.getElementById('values-form:submit-button');
$(document).ready(
    redrawDot()
)
