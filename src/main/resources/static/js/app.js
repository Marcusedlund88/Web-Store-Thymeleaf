window.addEventListener('load', function() {
    sessionStorage.clear();
});

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
    window.location.href = "/orders/" + id + "/delete";
}
function placeOrder(event, customerId) {
    event.preventDefault();

    let itemId = document.getElementById('itemOption').value;
    let quantity = document.getElementById('quantityOption').value;

    let orderItems = [];
    try {
        const orderItemsString = sessionStorage.getItem('orderItems');
        if (orderItemsString !== null) {
            orderItems = JSON.parse(orderItemsString);
        }
    } catch (error) {
        console.log('No order items found in session storage.');
    }

    console.log(orderItems);
    for (let i = 0; i < quantity; i++) {
        orderItems.push({ itemId: itemId});
    }

    console.log(orderItems);
    let table = document.querySelector('.table-body');
    table.innerHTML = "";

    orderItems.forEach(element =>{
        let newRow = table.insertRow();
        let trCustomer = newRow.insertCell();
        let trItem = newRow.insertCell();
        trCustomer.textContent = customerId;
        trItem.textContent = element.itemId;
    });

    sessionStorage.setItem('orderItems', JSON.stringify(orderItems));

    //window.location.href = endpoint;
}
function getOrderById(event, endpoint){
    event.preventDefault();
    window.location = endpoint;
}
function redirectToOrder(event){
    event.preventDefault();
    let input = document.getElementById('inputValue').value;
    console.log(input);
    window.location = "/orders/" + input
}
function makeOrderByCustomer(event, id){
    window.location = "/orders/" + id + "/create";
}

function testBuy(event, customerId){

    let button = document.getElementById('buttonBuy');

    event.preventDefault();

    fetch('/customers/fetch/'+ customerId)
        .then(response => response.json())
        .then(data => {
            console.log(data);
        })
        .catch(error => console.error(error));


    let itemJson = [];
    let itemsList = [];
    try {
        const orderItemsString = sessionStorage.getItem('orderItems');

        if (orderItemsString !== null) {
            orderItems = JSON.parse(orderItemsString);
            const fetchPromises = orderItems.map(element =>{
                return fetch('/items/fetch/'+ element.itemId)
                    .then(response => response.json())
                    .then(item => {
                        itemsList.push(item);
                    })
                    .catch(error => console.error(error));
            });

            Promise.all(fetchPromises).then(() => {
                for(let i = 0; i < itemsList.length; i++){
                    let string = JSON.stringify(itemsList[i]);
                    itemJson.push(string);
                }
                let string2 = JSON.stringify(itemsList);
                postOrder(string2, customerId);
            });
        }
    } catch (error) {
        console.log('No order items found in session storage.');
    }
}

function createCustomerJson(id, name, ssn) {
    let customer = {
        "id": id,
        "name": name,
        "ssn": ssn
    };
    return JSON.stringify(customer);
}
function createItemJson(id, name, price) {
    let customer = {
        "id": id,
        "name": name,
        "price": price
    };
    return JSON.stringify(customer);
}
function postOrder(jsonObject, customerId){
    console.log(jsonObject);

    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: jsonObject//JSON.stringify(jsonObject)
    };

    fetch('/orders/buy/' + customerId, options)
        .then(response => response.json())
        .then(data => console.log(data))
        .catch(error => console.error(error));
}