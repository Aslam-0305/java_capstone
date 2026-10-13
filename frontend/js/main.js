function loadDashboard(role) {
    document.getElementById('userDisplay').textContent = `${localStorage.getItem('username')} (${role})`;
    
    const sidebarNav = document.getElementById('sidebarNav');
    sidebarNav.innerHTML = '';

    const menus = {
        'ADMIN': [
            { label: 'Add Doctor', action: () => showAddDoctorForm() },
            { label: 'View Doctors', action: () => loadDoctorsList() },
            { label: 'View Appointments', action: () => loadAllAppointments() }
        ],
        'DOCTOR': [
            { label: 'Dashboard', action: () => showDoctorDashboard() },
            { label: 'My Appointments', action: () => loadDoctorAppointments() },
            { label: 'My Patients', action: () => loadMyPatients() }
        ],
        'PATIENT': [
            { label: 'Dashboard', action: () => showPatientDashboard() },
            { label: 'Find Doctor', action: () => showFindDoctor() },
            { label: 'My Appointments', action: () => loadPatientAppointments() },
            { label: 'My Medical History', action: () => showMedicalHistory() }
        ]
    };

    menus[role]?.forEach(menu => {
        const btn = document.createElement('button');
        btn.textContent = menu.label;
        btn.addEventListener('click', menu.action);
        sidebarNav.appendChild(btn);
    });

    // Load default content
    if (role === 'ADMIN') showAddDoctorForm();
    else if (role === 'DOCTOR') showDoctorDashboard();
    else if (role === 'PATIENT') showPatientDashboard();
}

// Admin Functions
function showAddDoctorForm() {
    const content = document.getElementById('content');
    content.innerHTML = `
        <div class="card">
            <h3>Add New Doctor</h3>
            <form onsubmit="handleAddDoctor(event)">
                <div class="form-group">
                    <label>Full Name</label>
                    <input type="text" id="docFullName" required>
                </div>
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" id="docEmail" required>
                </div>
                <div class="form-group">
                    <label>Phone</label>
                    <input type="text" id="docPhone" required>
                </div>
                <div class="form-group">
                    <label>Specialization</label>
                    <input type="text" id="docSpecialization" required>
                </div>
                <div class="form-group">
                    <label>License Number</label>
                    <input type="text" id="licenseNumber" required>
                </div>
                <div class="form-group">
                    <label>Experience (Years)</label>
                    <input type="number" id="experience" required>
                </div>
                <button type="submit" class="btn btn-primary">Add Doctor</button>
            </form>
        </div>
    `;
}

async function handleAddDoctor(event) {
    event.preventDefault();
    alert('Doctor added successfully!');
    showAddDoctorForm();
}

async function loadDoctorsList() {
    try {
        const doctors = await api.getAllDoctors();
        const content = document.getElementById('content');
        let html = '<h3>All Doctors</h3><table><tr><th>Name</th><th>Specialization</th><th>Phone</th><th>Actions</th></tr>';
        
        doctors.forEach(doctor => {
            html += `<tr><td>${doctor.fullName}</td><td>${doctor.specialization}</td><td>${doctor.phone}</td><td><button class="btn btn-primary">View</button></td></tr>`;
        });
        
        html += '</table>';
        content.innerHTML = html;
    } catch (error) {
        alert('Error loading doctors: ' + error.message);
    }
}

async function loadAllAppointments() {
    const content = document.getElementById('content');
    content.innerHTML = '<h3>All Appointments</h3><p>Loading...</p>';
}

// Doctor Functions
function showDoctorDashboard() {
    const content = document.getElementById('content');
    content.innerHTML = `
        <h2>Welcome, Doctor</h2>
        <div class="card">
            <h3>Today's Appointments</h3>
            <button class="btn btn-primary" onclick="loadDoctorAppointments()">View</button>
        </div>
    `;
}

async function loadDoctorAppointments() {
    try {
        const doctorId = getUserId();
        const appointments = await api.getDoctorAppointments(doctorId);
        const content = document.getElementById('content');
        
        let html = '<h3>My Appointments</h3>';
        if (appointments.length === 0) {
            html += '<p>No appointments</p>';
        } else {
            html += '<table><tr><th>Patient</th><th>Date</th><th>Time</th><th>Reason</th><th>Status</th></tr>';
            appointments.forEach(apt => {
                html += `<tr><td>Patient</td><td>${apt.appointmentDate}</td><td>${apt.startTime}</td><td>${apt.reasonForVisit}</td><td>${apt.status}</td></tr>`;
            });
            html += '</table>';
        }
        content.innerHTML = html;
    } catch (error) {
        alert('Error loading appointments: ' + error.message);
    }
}

async function loadMyPatients() {
    const content = document.getElementById('content');
    content.innerHTML = '<h3>My Patients</h3><p>Patient list will be displayed here</p>';
}

// Patient Functions
function showPatientDashboard() {
    const content = document.getElementById('content');
    content.innerHTML = `
        <h2>Welcome, Patient</h2>
        <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 1rem;">
            <div class="card">
                <h3>Book Appointment</h3>
                <button class="btn btn-primary" onclick="showFindDoctor()">Book Now</button>
            </div>
            <div class="card">
                <h3>My Appointments</h3>
                <button class="btn btn-primary" onclick="loadPatientAppointments()">View</button>
            </div>
            <div class="card">
                <h3>Medical History</h3>
                <button class="btn btn-primary" onclick="showMedicalHistory()">View</button>
            </div>
        </div>
    `;
}

function showFindDoctor() {
    const content = document.getElementById('content');
    content.innerHTML = `
        <div class="card">
            <h3>Find Doctor</h3>
            <div class="form-group">
                <label>Specialization</label>
                <input type="text" id="speciality" placeholder="e.g., Cardiology">
                <button class="btn btn-primary" onclick="searchDoctors()">Search</button>
            </div>
            <div id="doctorResults"></div>
        </div>
    `;
}

async function searchDoctors() {
    try {
        const specialization = document.getElementById('speciality').value;
        const doctors = await api.getDoctorsBySpecialization(specialization);
        
        let html = '<div class="doctor-cards">';
        doctors.forEach(doctor => {
            html += `
                <div class="doctor-card">
                    <h4>${doctor.fullName}</h4>
                    <p><strong>${doctor.specialization}</strong></p>
                    <p>Experience: ${doctor.experienceYears} years</p>
                    <p>Fee: ₹${doctor.consultationFee}</p>
                    <button class="btn btn-primary" onclick="bookAppointment(${doctor.id})">Book Now</button>
                </div>
            `;
        });
        html += '</div>';
        
        document.getElementById('doctorResults').innerHTML = html;
    } catch (error) {
        alert('Error searching doctors: ' + error.message);
    }
}

function bookAppointment(doctorId) {
    alert(`Appointment booking for doctor ID: ${doctorId}`);
}

async function loadPatientAppointments() {
    try {
        const patientId = getUserId();
        const appointments = await api.getPatientAppointments(patientId);
        const content = document.getElementById('content');
        
        let html = '<h3>My Appointments</h3>';
        if (appointments.length === 0) {
            html += '<p>No appointments</p>';
        } else {
            html += '<table><tr><th>Doctor</th><th>Date</th><th>Time</th><th>Reason</th><th>Status</th><th>Actions</th></tr>';
            appointments.forEach(apt => {
                html += `<tr><td>Doctor Name</td><td>${apt.appointmentDate}</td><td>${apt.startTime}</td><td>${apt.reasonForVisit}</td><td>${apt.status}</td><td><button class="btn btn-primary">Update</button></td></tr>`;
            });
            html += '</table>';
        }
        content.innerHTML = html;
    } catch (error) {
        alert('Error loading appointments: ' + error.message);
    }
}

function showMedicalHistory() {
    const content = document.getElementById('content');
    content.innerHTML = `
        <h3>Medical History</h3>
        <table>
            <tr><th>Date</th><th>Doctor</th><th>Diagnosis</th><th>Treatment</th></tr>
            <tr><td>2025-02-15</td><td>Dr. Smith</td><td>Hypertension</td><td>Medication</td></tr>
        </table>
    `;
}
