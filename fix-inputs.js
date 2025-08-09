const fs = require('fs');
const path = require('path');

// Leer el archivo InfoPersonal.jsx
const filePath = './frontend/src/pages/InfoPersonal.jsx';
let content = fs.readFileSync(filePath, 'utf8');

// Lista de nombres de campos que necesitan ser corregidos
const fieldNames = [
    'numeroDocumento', 'paisNacimiento', 'ciudadNacimiento',
    'telefonoCelular', 'correo', 'direccion', 'eps',
    'primerAcudiente', 'segundoAcudiente', 'apellidosAcudiente',
    'telefonoAcudiente', 'correoAcudiente'
];

// Función para reemplazar inputs
fieldNames.forEach(fieldName => {
    // Patrón para encontrar inputs sin key y value correcto
    const pattern = new RegExp(
        `(<input\\s+[^>]*name="${fieldName}"[^>]*value={formData\\.${fieldName}})([^>]*>)`,
        'g'
    );
    
    content = content.replace(pattern, (match, p1, p2) => {
        // Agregar key si no existe
        if (!match.includes('key=')) {
            p1 = p1.replace(`name="${fieldName}"`, `key="${fieldName}-input"\n              name="${fieldName}"`);
        }
        
        // Cambiar value para incluir || ''
        p1 = p1.replace(`value={formData.${fieldName}}`, `value={formData.${fieldName} || ''}`);
        
        return p1 + p2;
    });
});

// Escribir el archivo modificado
fs.writeFileSync(filePath, content, 'utf8');
console.log('Archivo InfoPersonal.jsx ha sido corregido exitosamente');
