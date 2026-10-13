const API_BASE_URL = 'http://localhost:8080/api';

const api = {
    // Auth APIs
    login: async (username, password) => {
        const response = await fetch(`${API_BASE_URL}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });
        return response.json();
    },

    register: async (user) => {
        const response = await fetch(`${API_BASE_URL}/auth/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(user)
        });
        return response.json();
    },

    // Doctor APIs
    getAllDoctors: async () => {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_BASE_URL}/doctors`, {
            headers: { 'Authorization': `Bearer ${token}` }
        });
        return response.json();
    },

    getDoctorsBySpecialization: async (specialization) => {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_BASE_URL}/doctors/specialization/${specialization}`, {
            headers: { 'Authorization': `Bearer ${token}` }
        });
        return response.json();
    },

    addDoctor: async (doctor) => {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_BASE_URL}/doctors`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(doctor)
        });
        return response.json();
    },

    // Appointment APIs
    createAppointment: async (appointment) => {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_BASE_URL}/appointments`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(appointment)
        });
        return response.json();
    },

    getPatientAppointments: async (patientId) => {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_BASE_URL}/appointments/patient/${patientId}`, {
            headers: { 'Authorization': `Bearer ${token}` }
        });
        return response.json();
    },

    getDoctorAppointments: async (doctorId) => {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_BASE_URL}/appointments/doctor/${doctorId}`, {
            headers: { 'Authorization': `Bearer ${token}` }
        });
        return response.json();
    },

    updateAppointment: async (id, appointment) => {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_BASE_URL}/appointments/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(appointment)
        });
        return response.json();
    },

    cancelAppointment: async (id) => {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_BASE_URL}/appointments/${id}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${token}` }
        });
        return response.ok;
    }
};
