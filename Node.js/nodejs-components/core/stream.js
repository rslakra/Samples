const path = require('path');
const fs = require('fs');

parentFolder = path.dirname(__dirname, "..")
console.log("parentFolder: " + parentFolder);
readFilePath = path.join(parentFolder, "data", "large.txt")
console.log("readFilePath: " + readFilePath);
writeFilePath = path.join(parentFolder, "data", "large-write.txt")
console.log("writeFilePath: " + writeFilePath);

const readStream = fs.createReadStream(readFilePath, { encoding: "utf-8" });
const writeStream = fs.createWriteStream(writeFilePath);

let counter = 0;
readStream.on('data', chunk => {
    counter++;
    console.log(`Counter ${counter}, Chunk: ${chunk}`);
    console.log("\n");
    writeStream.write(chunk);
});
