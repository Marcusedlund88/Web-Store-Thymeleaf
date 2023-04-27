
/*CUSTOMER FUNCTIONS*/

function getCustomerByID(event, endpoint){
    event.preventDefault();
    window.location = endpoint;
}

function redirectToCustomer(event) {
    event.preventDefault();
    let input = document.getElementById('inputValue').value;
    console.log(input);
    window.location = "/customers/" + input
}

function addCustomer(event, endpoint){
    event.preventDefault();
    window.location = endpoint
}
function deleteCustomer(event, id){
   window.location = "/customers/" + id + "/delete";
}
function updateCustomer(event, id){
    window.location = "/customers/" + id + "/update";
}
function proceedToUpdateCustomer(event, object){
    event.preventDefault();
    console.log(object)
    window.location.href = "/customers/" + object + "/update/form"
}
function submitFormCustomer(event){
    event.preventDefault();
    var form = document.getElementById('myForm');
    var data = {};
    for (var i = 0; i < form.elements.length; i++) {
        var element = form.elements[i];
        if (element.type !== 'submit') {
            data[element.name] = element.value;
        }
    }
    var xhr = new XMLHttpRequest();
    xhr.open('PUT', '/customers/' + data.id + '/update/form/execute', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            window.location.href = '/customers';
        }
    };
    xhr.send(JSON.stringify(data));
}
/*ITEM FUNCTIONS*/

function getItemByID(event, endpoint){
    event.preventDefault();
    window.location = endpoint;
}
function redirectToItem(event) {
    event.preventDefault();
    let input = document.getElementById('inputValue').value;
    console.log(input);
    window.location = "/items/" + input
}
function addItem(event, endpoint){
    event.preventDefault();
    window.location = endpoint
}
function deleteItem(event, id){
    window.location = "/items/" + id + "/delete";
}
function updateItem(event, id){
    window.location = "/items/" + id + "/update";
}
function proceedToUpdateItem(event, object){
    event.preventDefault();
    console.log(object)
    window.location.href = "/items/" + object + "/update/form"
}
function submitFormItem(event){
    event.preventDefault();
    var form = document.getElementById('myForm');
    var data = {};
    for (var i = 0; i < form.elements.length; i++) {
        var element = form.elements[i];
        if (element.type !== 'submit') {
            data[element.name] = element.value;
        }
    }
    var xhr = new XMLHttpRequest();
    xhr.open('PUT', '/items/' + data.id + '/update/form/execute', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            window.location.href = '/items';
        }
    };
    console.log(data)
    xhr.send(JSON.stringify(data));
}

function updateOrder(event, id){
    event.preventDefault();
}

function deleteOrder(event, id){
    event.preventDefault();
}