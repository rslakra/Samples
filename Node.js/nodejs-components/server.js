
// import local module (name of file only - .js is not required.)
// const math = require('./core/math')

// destructure methods here.
const { add, subtract, multiply, divide } = require('./core/math')

console.log("Hello, Node.js");
console.log('\n');
// console.log(global);

// Math Module
console.log("Add: " + add(2, 3));
console.log("Subtract: " + subtract(5, 3));
console.log("Multiply: " + multiply(2, 4));
console.log("Divide: " + divide(20, 3));
console.log('\n');

