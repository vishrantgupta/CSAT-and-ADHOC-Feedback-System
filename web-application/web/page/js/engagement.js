var engagementData = ["addr0"];
$(document).ready(function() {
    $('#engagementTable').dataTable();
    $("#addEngagementHeading, #addEngagementTbl, #addClientHeading, #addNewClientDetails").hide();

    $('#addEngagement').click(function(){
        $("#addClientHeading, #addNewClientDetails").hide();
        $("#addEngagementHeading, #addEngagementTbl").fadeToggle("fast");
    });

    $('#addClientDetails').click(function(){
        $("#addEngagementHeading, #addEngagementTbl").hide();
        $("#addClientHeading, #addNewClientDetails").fadeIn("fast", function() {
            showWait();

            var urlString = root + contextRoot + "/page/engagement.do?action=getAvailableEngagements";
            ajaxCall(urlString, "getAvailableEngagements", true);
        });
    });
    
    var i=1;
    $("#add_row").click(function(){
        if(i > 9) {
           alert("More than 10 rows are not allowed.");
           return false;
        }
        $('#addr'+i).html("<td>"+ (i+1) +"</td><td><select name='engagement' id='engagementSelectBox" + i + "' type='text' class='form-control input-md engagementSelectBox row" + i + "'  /> </td><td><input  name='firstName' type='text' placeholder='First Name'  class='form-control input-md cFirstName row" + i + "'></td><td><input  name='lastName' type='text' placeholder='Last Name'  class='form-control input-md cLastName row" + i + "'></td>");

        var $options = $("#engagementSelectBox0 > option").clone();
        $('#engagementSelectBox' + i).append($options);

        document.getElementById("engagementSelectBox" + i).selectedIndex = document.getElementById("engagementSelectBox" + (i - 1)).selectedIndex;

        $('#tab_logic').append('<tr id="addr'+(i+1)+'"></tr>');
        engagementData.push("row" + i);
        i++;
    });
    $("#delete_row").click(function () {
        if (i > 1) {
            $("#addr" + (i - 1)).html('');
            i--;
        }
        var index = engagementData.indexOf("row" + i);
        if (index > -1) {
            engagementData.splice(index, 1);
        }
    });
    
} );

function getAvailableEngagements(response) {

    $("#engagementSelectBox0 option").remove();
    
    newoption = new Option("Select Engagement", "Select Engagement", false, false);
    document.getElementById("engagementSelectBox0").options.add(newoption);
    newoption = null;
    
    $(response).find("engagement").each(function() {
        var id = $(this).attr("id");
        var name = $(this).attr("name");

        newoption = new Option(name, id, false, false);
        document.getElementById("engagementSelectBox0").options.add(newoption);
        newoption = null;
    });
    
    hideWait();
}

function saveNewEngagement() {
    var engagementName = (document.getElementById("addEngagementName").value).trim();
    var engagementDesc = (document.getElementById("addEngagementDesc").value).trim();
    
    if((typeof engagementName != 'undefined') && (typeof engagementDesc != 'undefined')) {
        if (engagementName == "") {
            alert("Please enter value for engagement name");
        } else if (engagementDesc == "") {
            alert("Please enter value for engagement description");
        } else {
            var urlString = root + contextRoot + "/page/engagement.do?action=saveNewEngagement";
            var parameterSrt = "&engagementName=" + engagementName + "&engagementDesc=" + engagementDesc;

            showWait();
            ajaxCallWithFailureHandler(urlString, parameterSrt, "showSuccessMsg", "ajaxFailHandle");
            return false;
        }
    } else {
        alert("Error occured.");
    }
    return false;
}

function showSuccessMsg(response) {
    
    var userMsg = $(response).find("userMsg").text();
    hideWait();
    
    if (userMsg != "") {
        alert(userMsg);
    } else {
        alert("Engagement is saved successfully.");
    }
}

function ajaxFailHandle (e) {
    alert("Error occured, please try again.");
}

function ClientDetails(engagementId, firstName, lastName) {
    this.engagementId = engagementId;
    this.firstName = firstName;
    this.lastName = lastName;
    
    return this;
}

var clientDetailsArray = [];
function submitClientDetails() {
    
    clientDetailsArray = [];
    
    var checkDataArray = ['engagementSelectBox', 'cFirstName', 'cLastName'];
    if (checkData(checkDataArray)) {
        for (var i = 0; i < engagementData.length; i++) {
            var engagement = document.getElementById('addr'+i).getElementsByTagName("select");
            var names = document.getElementById('addr'+i).getElementsByTagName("input");
            
            var firstName = "";
            var lastName = "";
            
            for(var j = 0; j < names.length; j++) {
                if(names[j].name === "firstName") {
                    if(!checkSpecialCharacter(names[j])){
                        firstName = names[j].value;
                    } else {
                        return false;
                    }
                }
                if (names[j].name === "lastName") {
                    if(!checkSpecialCharacter(names[j])){
                        lastName = names[j].value;
                    } else {
                        return false;
                    }
                }
            }
            var client = new ClientDetails(engagement[0].value, firstName, lastName);
            clientDetailsArray.push(client);
        }
        
        var data = "";
        for(var j = 0; j < clientDetailsArray.length; j++) {
            data += clientDetailsArray[j].engagementId + "|" + clientDetailsArray[j].firstName + "|" + clientDetailsArray[j].lastName + "#";
        }
        
        var urlString = root + contextRoot + "/page/engagement.do?action=saveClientDetails";
        var parameterSrt = "&data=" + data;

        showWait();
        ajaxCallWithFailureHandler(urlString, parameterSrt, "clientSaved", "ajaxFailHandle");
    }
}

function clientSaved(response) {
    
    var userMsg = $(response).find("userMsg").text();
    hideWait();
    
    if (userMsg != "" && userMsg != "true") {
        alert(userMsg);
    } else {
        alert("Client details are saved successfully.");
    }
}

function checkData(elementClassNameArray) {
    for(var i = 0; i < elementClassNameArray.length; i++) {
        var elementsByClass = getElementsByClassName(document.body, elementClassNameArray[i]);
        for (var j = 0; j < elementsByClass.length; j++) {
            var elementObj = elementsByClass[j];

            if(elementObj.tagName.toUpperCase() == "SELECT" && elementObj.selectedIndex == 0) {
                alert("Please fill all the fields.");
                return false;
            } else if (elementObj.tagName.toUpperCase() == "INPUT" && elementObj.value == "") {
                alert("Please fill all the fields.");
                return false;
            }
        }
    }
    return true;
}