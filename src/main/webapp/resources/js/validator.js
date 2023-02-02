export class Validator {
    xMin;
    xMax;
    yMin;
    yMax;
    rValues;
    CHOOSE_X_LABEL = 'You must select X';
    CHOOSE_Y_LABEL = 'You must select Y';
    CHOOSE_R_LABEL = 'You must select R';
    WRONG_Y_LABEL = 'Y must be in {-5;3}';
    WRONG_X_LABEL = 'X must be in {-3,3}';
    WRONG_R_LABEL = 'R must be in {1,2,3,4,5}';


    constructor(xMin,xMax, yMin, yMax, rValues) {
        this.xMin=xMin;
        this.xMax=xMax;
        this.yMin=yMin;
        this.yMax=yMax;
        this.rValues = rValues;
    }

    isEmpty(value) {
        return value === null || value.trim().length === 0;
    }

    isNumber(value) {
        return !isNaN(value) || isFinite(value);
    }

    checkY(value) {
        return value >= this.yMin && value <= this.yMax;
    }

    checkX(value) {
        return value >= this.xMin && value <= this.xMax;
    }

    checkR(value) {
        return this.rValues.includes(value);
    }


    validateX(xValue) {
        return !this.isEmpty(xValue) && this.checkX(Number(xValue));
    }


    validateY(yValue) {
        return !this.isEmpty(yValue) && this.checkY(Number(yValue));
    }

    validateR(rValue) {
        return rValue>0;
    }

    validateRValueFromGraph(countRValue){
        return countRValue===1;
    }

    validate

    validateAllInputValues(xValue, yValue, rValue, xButton, yInput, rSelect, xButtons) {
        this.deleteAllErrorMessage(xButtons, yInput, rSelect);
        let valid = true;
        if (!this.validateX(xValue)) {
            if (this.isEmpty(xValue)){
                this.showErrorMessage(xButton, this.CHOOSE_X_LABEL)
            }else{
                this.showErrorMessage(xButton, this.WRONG_X_LABEL);
            }
            valid = false;
        }
        if (!this.validateY(yValue)) {
            if (this.isEmpty(yValue)) {
                this.showErrorMessage(yInput, this.CHOOSE_Y_LABEL);
            } else {
                this.showErrorMessage(yInput, this.WRONG_Y_LABEL);
            }
            valid = false;
        }
        if (!this.validateR(rValue)) {
                this.showErrorMessage(rSelect, this.CHOOSE_R_LABEL);
            valid = false;
        }
        return valid;
    }

    createWrongLabel(message) {
        return `<p class="wrong-value" style="left: 75.5%; top: 80%;">${message}</p>`;
    }



    showErrorMessage(value, message) {
        value.after(this.createWrongLabel(message));
    }

    deleteErrorMessage(value) {
        value.siblings().remove('.wrong-value')
    }

    deleteAllErrorMessage(x, y, r) {
        x.siblings().remove('.wrong-value');
        y.siblings().remove('.wrong-value');
        r.siblings().remove('.wrong-value');
    }

}
