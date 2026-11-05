const fsPromises = require('fs').promises;
const path = require('path');

parentFolder = path.dirname(__dirname, "..")
console.log("parentFolder: " + parentFolder);
readFilePath = path.join(parentFolder, "data", "small.txt")
console.log("readFilePath: " + readFilePath);
writeFilePath = path.join(parentFolder, "data", "promises.txt")
console.log("writeFilePath: " + writeFilePath);

// Create a function to read/write file contents.
const fileOps = async () => {
    try {
        // Step 1: Read the file
        const data = await fsPromises.readFile(readFilePath, "utf-8");
        console.log(data);

        // Step 1.1: Delete the file
        await fsPromises.unlink(readFilePath);

        // Step 2: Write data to file
        await fsPromises.writeFile(writeFilePath, data);

        // Step 3: Append contents to the file.
        contents = "Great file contents.";
        await fsPromises.appendFile(writeFilePath, contents);

        // Step 4: Read the new file contents
        const newData = await fsPromises.readFile(writeFilePath, "utf-8");
        console.log(newData);
    } catch (err) {
        console.error(err);
    }
}


fileOps();
