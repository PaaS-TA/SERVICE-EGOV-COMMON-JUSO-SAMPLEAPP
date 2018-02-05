'use strict';

/* Common function */

/*
null, undefined 를 ""로 변경하기
*/
function checkNullorUndefined(str) {
    var strResult = "";

    if (str === undefined || str === null) {
      return "";
    }

    return str;
}


function waitFormModal(value) {

    if (value) {

        $("#waitFormModal").modal("show");

    } else {

        $("#waitFormModal").modal("hide");

    }
}


/*
LOG
*/
function log(status, msg) {

    console.log(status + '] ' + msg);

    if (status === 'ERROR') {
        alert(msg);
    }
}
