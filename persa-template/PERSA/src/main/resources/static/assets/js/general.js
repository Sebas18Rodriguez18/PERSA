$(document).ready(function () {
    function initializeDataTable() {
        if (window.innerWidth > 1520) {
            if (!$.fn.DataTable.isDataTable('#table_data')) {
                const dataTableOptions = {
                    pageLength: 10,
                    responsive: true,
                    lengthChange: false,
                    language: {
                        paginate: {
                            previous: "Anterior",
                            next: "Siguiente"
                        },
                        search: "Buscar:",
                        info: "Mostrando _START_ a _END_ de _TOTAL_ registros",
                        infoEmpty: "",
                        emptyTable: "No existen registros"
                    }
                };
                $('#table_data').DataTable(dataTableOptions);
            }
        } else {
            if ($.fn.DataTable.isDataTable('#table_data')) {
                $('#table_data').DataTable().destroy();
            }
        }
    }

    initializeDataTable();

    $(window).resize(function () {
        initializeDataTable();
    });
});

function remove(event, id) {
    event.preventDefault();
    Swal.fire({
        title: "¿Estas seguro?",
        text: "¡Esta acción no se puede revertir!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "eliminar",
        cancelButtonText: "cancelar"
    }).then((result) => {
        if (result.isConfirmed) {

            const form = document.getElementById(`form-delete-${id}`);
            if (form) {
                form.submit();
            }

        }
    });
}

function update() {
    Swal.fire({
        position: 'top-end',
        icon: 'success',
        title: 'Actualizado correctamente',
        showConfirmButton: false,
        timer: 1500
    });
}

function create() {
    Swal.fire({
        title: 'Creado exitosamente',
        text: 'El registro se ha creado con exitosamente',
        icon: 'success',
        confirmButtonText: 'Aceptar'
    });
}

document.addEventListener("DOMContentLoaded", () => {
    const body = document.querySelector("body");
    const success = body.dataset.success;
    const error = body.dataset.error;

    if (success) {
        Swal.fire({
            icon: 'success',
            title: '¡Éxito!',
            text: success,
            showConfirmButton: false,
            timer: 3000
        });
    }

    if (error) {
        Swal.fire({
            icon: 'error',
            title: '¡Error!',
            text: error,
            showConfirmButton: true
        });
    }
});