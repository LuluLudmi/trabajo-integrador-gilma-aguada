// Configuración de la API - Usando Axios (requisito del enunciado)
const API_URL = 'http://localhost:8080/api/productos';
let modalInstance;

// Cargar productos al iniciar
document.addEventListener('DOMContentLoaded', () => {
    cargarProductos();
    modalInstance = new bootstrap.Modal(document.getElementById('productoModal'));
});

// Función para cargar todos los productos usando Axios
async function cargarProductos() {
    try {
        const response = await axios.get(API_URL);
        mostrarProductos(response.data);
    } catch (error) {
        console.error('Error:', error);
        mostrarError('No se pudieron cargar los productos');
    }
}

// Función para mostrar productos en la tabla
function mostrarProductos(productos) {
    const tbody = document.getElementById('tablaProductos');
    
    if (productos.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" class="text-center">No hay productos registrados</td></tr>';
        return;
    }
    
    tbody.innerHTML = productos.map(producto => {
        const estadoStock = producto.stock < 10 ? 
            '<span class="badge bg-danger">Bajo Stock</span>' : 
            '<span class="badge bg-success">Disponible</span>';
        
        return `
            <tr>
                <td>${producto.idProducto}</td>
                <td>${producto.nombre}</td>
                <td>$${producto.precio.toFixed(2)}</td>
                <td>${producto.stock}</td>
                <td>${estadoStock}</td>
                <td>
                    <button class="btn btn-sm btn-warning" onclick="editarProducto(${producto.idProducto})">
                        <i class="bi bi-pencil"></i>
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="eliminarProducto(${producto.idProducto})">
                        <i class="bi bi-trash"></i>
                    </button>
                </td>
            </tr>
        `;
    }).join('');
}

// Función para buscar productos por nombre usando Axios
async function buscarProductos() {
    const nombre = document.getElementById('buscarNombre').value;
    
    if (!nombre.trim()) {
        cargarProductos();
        return;
    }
    
    try {
        const response = await axios.get(`${API_URL}/buscar`, {
            params: { nombre: nombre }
        });
        mostrarProductos(response.data);
    } catch (error) {
        console.error('Error:', error);
        mostrarError('Error al buscar productos');
    }
}

// Cargar productos con stock disponible
async function cargarConStock() {
    try {
        const response = await axios.get(`${API_URL}/con-stock`);
        mostrarProductos(response.data);
    } catch (error) {
        console.error('Error:', error);
        mostrarError('Error al cargar productos con stock');
    }
}

// Cargar productos con bajo stock
async function cargarBajoStock() {
    try {
        const response = await axios.get(`${API_URL}/bajo-stock`);
        mostrarProductos(response.data);
    } catch (error) {
        console.error('Error:', error);
        mostrarError('Error al cargar productos con bajo stock');
    }
}

// Abrir modal para nuevo producto
function abrirModalNuevo() {
    document.getElementById('modalTitle').textContent = 'Nuevo Producto';
    document.getElementById('formProducto').reset();
    document.getElementById('productoId').value = '';
}

// Función para editar producto usando Axios
async function editarProducto(id) {
    try {
        const response = await axios.get(`${API_URL}/${id}`);
        const producto = response.data;
        
        document.getElementById('modalTitle').textContent = 'Editar Producto';
        document.getElementById('productoId').value = producto.idProducto;
        document.getElementById('nombre').value = producto.nombre;
        document.getElementById('precio').value = producto.precio;
        document.getElementById('stock').value = producto.stock;
        
        modalInstance.show();
    } catch (error) {
        console.error('Error:', error);
        mostrarError('Error al cargar los datos del producto');
    }
}

// Función para guardar producto usando Axios
async function guardarProducto() {
    const id = document.getElementById('productoId').value;
    const producto = {
        nombre: document.getElementById('nombre').value,
        precio: parseFloat(document.getElementById('precio').value),
        stock: parseInt(document.getElementById('stock').value)
    };
    
    try {
        if (id) {
            await axios.put(`${API_URL}/${id}`, producto);
            mostrarExito('Producto actualizado correctamente');
        } else {
            await axios.post(API_URL, producto);
            mostrarExito('Producto creado correctamente');
        }
        
        modalInstance.hide();
        cargarProductos();
    } catch (error) {
        console.error('Error:', error);
        mostrarError(error.response?.data?.message || 'Error al guardar el producto');
    }
}

// Función para eliminar producto usando Axios
async function eliminarProducto(id) {
    if (!confirm('¿Está seguro de eliminar este producto?')) return;
    
    try {
        await axios.delete(`${API_URL}/${id}`);
        cargarProductos();
        mostrarExito('Producto eliminado correctamente');
    } catch (error) {
        console.error('Error:', error);
        mostrarError('Error al eliminar el producto');
    }
}

// Funciones auxiliares
function mostrarError(mensaje) {
    alert('Error: ' + mensaje);
}

function mostrarExito(mensaje) {
    alert(mensaje);
}
