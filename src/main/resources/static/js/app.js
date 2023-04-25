
function redirectToCustomer(event) {
    event.preventDefault();
        let input = document.getElementById('inputValue').value;

        console.log(input)
        window.location = '/customers/' + input;
}