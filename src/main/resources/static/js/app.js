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
