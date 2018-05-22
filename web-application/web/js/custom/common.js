/*
 * Ajax call
 * 
 * @param {type} startDate
 * @param {type} endDate
 * @param {type} dateRange
 * @returns {Boolean}
 * 
 */
function ajaxCall(urlString,responseFunctionName, showErrorMsg){			
    showWait();
    $.ajax({
        type: "POST",  
        url: urlString,    
        success:function(response){  
            window[responseFunctionName](response);  
        },  
        error: function(e){
            if (showErrorMsg) {    
                alert("Error Occured: Please try again.");
            }
        }
    });
}

function ajaxCallWithFailureHandler(urlString, parameters, responseFunctionName, failureHandlerFunctionName) {
    showWait();
    $.ajax({
        type: "POST",
        url: urlString,
        data: parameters,
        success: function(response) {
            window[responseFunctionName](response);
        },
        error: function(e) {
            window[failureHandlerFunctionName](e);
        }
    });
}

function disableDocumentFields(doc, flag) {

    var inputBoxes = doc.getElementsByTagName("input");
    for (var i = 0; i < inputBoxes.length; i++)
        inputBoxes[i].disabled = flag;

    inputBoxes = doc.getElementsByTagName("select");
    for (i = 0; i < inputBoxes.length; i++)
        inputBoxes[i].disabled = flag;

    inputBoxes = doc.getElementsByTagName("radio");
    inputBoxes.disabled = flag;

    inputBoxes = doc.getElementsByTagName("textarea");
    for (i = 0; i < inputBoxes.length; i++)
        inputBoxes[i].disabled = flag;

    inputBoxes = doc.getElementsByTagName("div");
    for (i = 0; i < inputBoxes.length; i++)
        inputBoxes[i].disabled = flag;

}

function pleaseWait() {
    $('body').css('cursor', 'wait');
}

function closePleaseWait() {
    $('body').css('cursor', 'auto');
}

/* This function is used to validate date range for n days */
function validateDateRangeForGivenDays(startDate, endDate, dateRange) {
    var date1_ms = new Date(startDate.toString());
    var date2_ms = new Date(endDate.toString());

    if (date1_ms > new Date() || date2_ms > new Date()) {
        alert("Start date or end date can not be greater than todays date.");
        return false;
    }

    var ONE_DAY = 1000 * 60 * 60 * 24;
    var days_left = Math.ceil((date2_ms.getTime() - date1_ms.getTime()) / ONE_DAY);

    var ecxatDays = days_left;

    if (days_left < 0) {
        alert("To date should be greater than from date.");
        return false;
    } else if (ecxatDays > dateRange) {
        alert("Date range should not be greater than " + dateRange + " days.");
        return false;
    } else {
        return true;
    }
}

window.onerror = function(msg, url, line, col, error) {
    var extra = !col ? '' : ' column: ' + col;
    extra += !error ? '' : ' error: ' + error;

    var errorMsg = "Error: " + msg + " url: " + url + " line: " + line + extra;
    reportJSError(errorMsg);
    
    alert(errorMsg);
    
    hideWait();
    // TODO make suppressErrorAlert = true
    var suppressErrorAlert = false;
    return suppressErrorAlert;
};

function reportJSError(errorMsg) {
    var urlString = root + contextRoot + "/reportJSException.do?action=reportJSException&jsException=" + errorMsg;
    ajaxCall(urlString, "exceptionReported", false);
}

function exceptionReported() {}

var BrowserDetect = {
    init: function () {
        this.browser = this.searchString(this.dataBrowser) || "Other";
        this.version = this.searchVersion(navigator.userAgent) || this.searchVersion(navigator.appVersion) || "Unknown";
    },
    searchString: function (data) {
        for (var i = 0; i < data.length; i++) {
            var dataString = data[i].string;
            this.versionSearchString = data[i].subString;

            if (dataString.indexOf(data[i].subString) !== -1) {
                return data[i].identity;
            }
        }
    },
    searchVersion: function (dataString) {
        var index = dataString.indexOf(this.versionSearchString);
        if (index === -1) {
            return;
        }

        var rv = dataString.indexOf("rv:");
        if (this.versionSearchString === "Trident" && rv !== -1) {
            return parseFloat(dataString.substring(rv + 3));
        } else {
            return parseFloat(dataString.substring(index + this.versionSearchString.length + 1));
        }
    },

    dataBrowser: [
        {string: navigator.userAgent, subString: "Chrome", identity: "Chrome"},
        {string: navigator.userAgent, subString: "MSIE", identity: "Explorer"},
        {string: navigator.userAgent, subString: "Trident", identity: "Explorer"},
        {string: navigator.userAgent, subString: "Firefox", identity: "Firefox"},
        {string: navigator.userAgent, subString: "Safari", identity: "Safari"},
        {string: navigator.userAgent, subString: "Opera", identity: "Opera"}
    ]

};
BrowserDetect.init();

window.alert = function(msg) {
    jAlert(msg, 'Alert');
};

function showWait() {
    enableDisableDoc(true);
    pleaseWait();
    $("#wait").show();
}
function hideWait() {
    enableDisableDoc(false);
    closePleaseWait();
    if($("#wait").length > 0) {
        $("#wait").hide();
    }
}

function enableDisableDoc(flag) {
    disableDocumentFields(document, flag);
}

function getElementsByClassName(node, classname) {
    var a = [];
    var re = new RegExp('(^| )' + classname + '( |$)');
    var els = node.getElementsByTagName("*");
    for (var i = 0, j = els.length; i < j; i++)
        if (re.test(els[i].className))
            a.push(els[i]);
    return a;
}

function checkSpecialCharacter(elementObj) {
    if(/^[a-zA-Z0-9- ]*$/.test(elementObj.value) == false) {
        showErrorCss(elementObj);
        alert("Special characters not allowed.");
        return true;
    } else {
        hideErrorCss(elementObj);
    }
    
    return false;
}

function showErrorCss(elementObj) {
    elementObj.parentNode.className += " has-error";
}

function hideErrorCss(elementObj) {
    elementObj.parentNode.className = elementObj.parentNode.className.replace("has-error", "");
}

// Return an array of the selected opion values select is an HTML select element
function getSelectValues(select) {
    var result = [];
    var options = select && select.options;
    var opt;

    for (var i = 0, iLen = options.length; i < iLen; i++) {
        opt = options[i];

        if (opt.selected) {
            result.push(opt.value || opt.text);
        }
    }
    return result;
}