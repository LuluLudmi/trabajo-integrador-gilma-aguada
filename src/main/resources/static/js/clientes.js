// Configuración de la API
const API_URL = 'http://localhost:8080/api/clientes';
let modalInstance;

// Cargar clientes al iniciar
document.addEventListener('DOMContentLoaded', () => {
    cargarClientes();
    modalInstance = new bootstrap.Modal(document.getElementById('clienteModal'));
});

// Función para cargar todos los clientes usando fetch
async function cargarClientes() {
    try {
        const response = await fetch(API_URL);
        if (!response.ok) throw new Error('Error al cargar clientes');
        
        const clientes = await response.json();
        mostrarClientes(clientes);
    } catch (error) {
        console.error('Error:', error);
        mostrarError('No se pudieron cargar los clientes');
    }
}

// Función para mostrar clientes en la tabla
function mostrarClientes(clientes) {
    const tbody = document.getElementById('tablaClientes');
    
    if (clientes.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5" class="text-center">No hay clientes registrados</td></tr>';
        return;
    }
    
    tbody.innerHTML = clientes.map(cliente => `
        <tr>
            <td>${cliente.idCliente}</td>
            <td>${cliente.nombre}</td>
            <td>${cliente.email}</td>
            <td>${cliente.telefono}</td>
            <td>
                <button class="btn btn-sm btn-warning" onclick="editarCliente(${cliente.idCliente})">
                    <i class="bi bi-pencil"></i>
                </button>
                <button class="btn btn-sm btn-danger" onclick="eliminarCliente(${cliente.idCliente})">
                    <i class="bi bi-trash"></i>
                </button>
            </td>
        </tr>
    `).join('');
}

// Función para buscar clientes por nombre
async function buscarClientes() {
    const nombre = document.getElementById('buscarNombre').value;
    
    if (!nombre.trim()) {
        cargarClientes();
        return;
    }
    
    try {
        const response = await fetch(`${API_URL}/buscar?nombre=${encodeURIComponent(nombre)}`);
        if (!response.ok) throw new Error('Error en la búsqueda');
        
        const clientes = await response.json();
        mostrarClientes(clientes);
    } catch (error) {
        console.error('Error:', error);
        mostrarError('Error al buscar clientes');
    }
}

// Abrir modal para nuevo cliente
function abrirModalNuevo() {
    document.getElementById('modalTitle').textContent = 'Nuevo Cliente';
    document.getElementById('formCliente').reset();
    document.getElementById('clienteId').value = '';
}

// Función para editar cliente
async function editarCliente(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`);
        if (!response.ok) throw new Error('Error al cargar cliente');
        
        const cliente = await response.json();
        
        document.getElementById('modalTitle').textContent = 'Editar Cliente';
        document.getElementById('clienteId').value = cliente.idCliente;
        document.getElementById('nombre').value = cliente.nombre;
        document.getElementById('email').value = cliente.email;
        document.getElementById('telefono').value = cliente.telefono;
        
        modalInstance.show();
    } catch (error) {
        console.error('Error:', error);
        mostrarError('Error al cargar los datos del cliente');
    }
}

// Función para guardar cliente (crear o actualizar)
async function guardarCliente() {
    const id = document.getElementById('clienteId').value;
    const cliente = {
        nombre: document.getElementById('nombre').value,
        email: document.getElementById('email').value,
        telefono: document.getElementById('telefono').value
    };
    
    try {
        const url = id ? `${API_URL}/${id}` : API_URL;
        const method = id ? 'PUT' : 'POST';
        
        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(cliente)
        });
        
        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || 'Error al guardar');
        }
        
        modalInstance.hide();
        cargarClientes();
        mostrarExito(id ? 'Cliente actualizado correctamente' : 'Cliente creado correctamente');
    } catch (error) {
        console.error('Error:', error);
        mostrarError(error.message);
    }
}

// Función para eliminar cliente
async function eliminarCliente(id) {
    if (!confirm('¿Está seguro de eliminar este cliente?')) return;
    
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) throw new Error('Error al eliminar');
        
        cargarClientes();
        mostrarExito('Cliente eliminado correctamente');
    } catch (error) {
        console.error('Error:', error);
        mostrarError('Error al eliminar el cliente');
    }
}

// Funciones auxiliares para mensajes
function mostrarError(mensaje) {
    alert('Error: ' + mensaje);
}

function mostrarExito(mensaje) {
    alert(mensaje);
}
