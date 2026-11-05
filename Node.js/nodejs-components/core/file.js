const fs = require('fs');
const path = require('path');

parentFolder = path.dirname(__dirname, "..")
console.log("parentFolder: " + parentFolder);
readFilePath = path.join(parentFolder, "data", "small.txt")
console.log("readFilePath: " + readFilePath);

// Reads the file and print contents
fs.readFile(readFilePath, 'utf-8', (err, data) => {
    if (err) return err;
    // console.log(data.toString());
    console.log(data);
});

console.log("End of file reading!");

writeFilePath = path.join(parentFolder, "data", "reply.txt")
console.log("writeFilePath: " + writeFilePath);
contents = "Great file contents.";
fs.writeFile(writeFilePath, contents, (err) => {
    if (err) return err;
    console.log('Write completed!')
});

// handle uncaughtException error
process.on('uncaughtException', (err) => {
    console.error(`Handled Error: ${err}`);
    process.exit(1);
});

