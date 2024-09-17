document.getElementById('studentForm').addEventListener('submit', async function (event) {
    event.preventDefault(); // Prevent form from submitting the traditional way

    // Create the request body object based on your API requirements
    const requestBody = {
        id: "", // You may want to generate this dynamically or handle it via the backend
        name: document.getElementById('txt-name').value,
        email: document.getElementById('txt-email').value,
        age: document.getElementById('txt-age').value,
        contact: document.getElementById('txt-contact').value,
        address: document.getElementById('txt-address').value,
        guardianName: document.getElementById('txt-Gname').value,
        guardianEmail: document.getElementById('txt-Gemail').value,
        guardianContact: document.getElementById('txt-Gcontact').value,
        guardianAddress: document.getElementById('txt-Gaddress').value
    };

    try {
        // Make POST request to API
        const response = await fetch('http://localhost:8080/student/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
        });

        if (response.ok) {
            const result = await response.json();
            alert('Registration successful!');
            console.log('Success:', result);
        } else {
            alert('Error during registration. Please try again.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Something went wrong. Please check the console for details.');
    }
});
