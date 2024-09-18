document.addEventListener('DOMContentLoaded', () => {

    async function getStudents() {
        const url = 'http://localhost:8080/student/getAll';
        try {
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data = await response.json();
            if (data.code === '00') {
                const content = data.content;
                const tableBody = document.getElementById('studentTableBody');
                tableBody.innerHTML = '';
                content.forEach(student => {
                    const row = document.createElement('tr');
                    row.dataset.id = student.id;
                    row.innerHTML = `
                        <td>${student.id}</td>
                        <td>${student.name}</td>
                        <td>${student.email}</td>
                        <td>${student.age}</td>
                        <td>${student.contact}</td>
                        <td>${student.address || ''}</td>
                        <td>${student.guardianName || ''}</td>
                        <td>${student.guardianEmail || ''}</td>
                        <td>${student.guardianContact || ''}</td>
                        <td>${student.guardianAddress || ''}</td>
                    `;
                    tableBody.appendChild(row);
                });
            } else {
                console.error('Error in response code:', data.code);
            }
        } catch (error) {
            console.error('Error fetching data:', data.code);
        }
    }


    async function handleFormSubmit(url, method, body) {
        try {
            const response = await fetch(url, {
                method: method,
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(body),
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const result = await response.json();

            if (result.code === '00') {
                document.getElementById('studentForm').reset();
                getStudents();
                showToast('Student Added successfully!');
            } else {
                console.error('Error in response code:', result.code);
            }
        } catch (error) {
            console.error('Error handling form submission:', result.code);
        }
    }


    document.getElementById('studentForm').addEventListener('submit', (event) => {
        event.preventDefault();

        const studentData = {
            id: document.getElementById('txt-id').value,
            name: document.getElementById('txt-name').value,
            email: document.getElementById('txt-email').value,
            age: document.getElementById('txt-age').value,
            contact: document.getElementById('txt-contact').value,
            address: document.getElementById('txt-address').value,
            guardianName: document.getElementById('txt-Gname').value,
            guardianEmail: document.getElementById('txt-Gemail').value,
            guardianContact: document.getElementById('txt-Gcontact').value,
            guardianAddress: document.getElementById('txt-Gaddress').value,
        };

        if (studentData.id) {

            handleFormSubmit('http://localhost:8080/student/update', 'PUT', studentData);
        } else {
            handleFormSubmit('http://localhost:8080/student/create', 'POST', studentData);
        }
    });


    async function deleteStudent(id) {
        try {
            const response = await fetch(`http://localhost:8080/student/delete/${id}`, {
                method: 'DELETE',
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const result = await response.json();
            if (result.code === '00') {
                getStudents();
                showToast('Student Deleted successfully!');
            } else {
                console.error('Error in response code:', result.code);
            }
        } catch (error) {
            console.error('Error deleting student:', result.code);
        }
    }

    function setData(row) {
        document.getElementById('txt-id').value = row.dataset.id;
        document.getElementById('txt-name').value = row.children[1].textContent;
        document.getElementById('txt-email').value = row.children[2].textContent;
        document.getElementById('txt-age').value = row.children[3].textContent;
        document.getElementById('txt-contact').value = row.children[4].textContent;
        document.getElementById('txt-address').value = row.children[5].textContent;
        document.getElementById('txt-Gname').value = row.children[6].textContent;
        document.getElementById('txt-Gemail').value = row.children[7].textContent;
        document.getElementById('txt-Gcontact').value = row.children[8].textContent;
        document.getElementById('txt-Gaddress').value = row.children[9].textContent;


        document.getElementById('id').textContent = row.dataset.id;
        document.getElementById('name').textContent = row.children[1].textContent;
        document.getElementById('email').textContent = row.children[2].textContent;
        document.getElementById('age').textContent = row.children[3].textContent;
        document.getElementById('contact').textContent = row.children[4].textContent;
        document.getElementById('address').textContent = row.children[5].textContent;
        document.getElementById('g-name').textContent = row.children[6].textContent;
        document.getElementById('g-email').textContent = row.children[7].textContent;
        document.getElementById('g-contact').textContent = row.children[8].textContent;
        document.getElementById('g-address').textContent = row.children[9].textContent;

    }

    document.getElementById('studentTableBody').addEventListener('click', (event) => {
        const row = event.target.closest('tr');
        if (row) {
            setData(row);
        }
    });


    document.querySelector('button[type="delete"]').addEventListener('click', () => {
        const id = document.getElementById('txt-id').value;
        if (id) {
            deleteStudent(id);
        } else {
            showErrorToast('No student selected for deletion.');
        }
    });

    document.querySelector('button[type="update"]').addEventListener('click', () => {
        const id = document.getElementById('txt-id').value;
        if (id) {
            const studentData = {
                id: id,
                name: document.getElementById('txt-name').value,
                email: document.getElementById('txt-email').value,
                age: document.getElementById('txt-age').value,
                contact: document.getElementById('txt-contact').value,
                address: document.getElementById('txt-address').value,
                guardianName: document.getElementById('txt-Gname').value,
                guardianEmail: document.getElementById('txt-Gemail').value,
                guardianContact: document.getElementById('txt-Gcontact').value,
                guardianAddress: document.getElementById('txt-Gaddress').value,
            };
            handleFormSubmit('http://localhost:8080/student/update', 'PUT', studentData);
            showToast('Student Updated successfully!');
        } else {
            showErrorToast('No student selected for update.');
        }
    });


    getStudents();

    function showToast(message) {
        const toastEl = document.getElementById('liveToast');
        const toast = new bootstrap.Toast(toastEl, {
            autohide: true,
            delay: 5000 
        });
        const toastBody = toastEl.querySelector('.toast-body');
        toastBody.textContent = message;
        toast.show();
    }

    function showErrorToast(message) {
        const toastEl = document.getElementById('liveToast-er');
        const toast = new bootstrap.Toast(toastEl, {
            autohide: true,
            delay: 5000 
        });
        const toastBody = toastEl.querySelector('.toast-body');
        toastBody.textContent = message;
        toast.show();
    }
    

});
