const os = require('os');
const fs = require('fs');
const path = require('path')

console.log("Hello, Node.js");
console.log('\n');
// console.log(global);

// console.log(os);

console.log("OS Details");

// OS Home Dir & Hostname
console.log("Home Dir: " + os.homedir());
console.log("Hostname: " + os.hostname());
console.log('\n');

// OS Type & Version
console.log("Type: " + os.type());
console.log("Version: " + os.version());
console.log("Machine: " + os.machine());
console.log('\n');

// OS Memory Info
console.log("Total Mem: " + os.totalmem());
console.log('\n');

// Get CPU information
const cpus = os.cpus();
let cpuCount = os.availableParallelism?.() || cpus.length;
console.log(`The system has ${cpuCount} logical CPU cores.`);
console.log("cpuCount: " + cpuCount);
// console.log("cpus: " + cpus);
console.log("CPU Details");
cpus.forEach((cpu, index) => {
    console.log(`\n--- Core ${index + 1} ---`);
    console.log(`Model: ${cpu.model}`);
    console.log(`Speed: ${cpu.speed} MHz`);
    console.log('CPU Times:');
    console.log(`  User: ${cpu.times.user} ms`);
    console.log(`  System: ${cpu.times.sys} ms`);
    console.log(`  Idle: ${cpu.times.idle} ms`);
    console.log(`  Nice: ${cpu.times.nice}`);
    console.log(`  IRQ: ${cpu.times.irq}`);
});
console.log('\n');

// console.log("CPU Details (For Loop)");
// let coreCount = 0;
// for (const cpu of cpus) {
//     coreCount++;
//     console.log(`\n--- Core ${coreCount} ---`);
//     console.log(`Model: ${cpu.model}`);
//     console.log(`Speed: ${cpu.speed} MHz`);
//     console.log('CPU Times:');
//     console.log(`  User: ${cpu.times.user} ms`);
//     console.log(`  System: ${cpu.times.sys} ms`);
//     console.log(`  Idle: ${cpu.times.idle} ms`);
//     console.log(`  Nice: ${cpu.times.nice}`);
//     console.log(`  IRQ: ${cpu.times.irq}`);
// }

// let cpuCount = os.availableParallelism?.() || cpuInfo.length;

try {
    const quota = fs.readFileSync('/sys/fs/cgroup/cpu.max', 'utf8').trim();
    const [maxQuota, maxPeriod] = quota.split(' ').map(Number);

    if (maxQuota > 0 && maxPeriod > 0) {
        cpuCount = Math.max(1, maxQuota / maxPeriod);
    }
} catch (e) {
    // If cgroup read fails, use the default cpuCount.
}

console.log(`Using ${cpuCount} logical CPU cores.`);

console.log('\n');

// console.log("UserInfo: " + os.userInfo());

// Temp Dir
console.log("Tmp Dir: " + os.tmpdir());
console.log('\n');



// Current Dir & File Names
console.log("__dirname: " + __dirname);
console.log("__filename: " + __filename);
console.log('\n');

// Path Module
console.log("dirname: " + path.dirname(__filename));
console.log("basename: " + path.basename(__filename));
console.log("extname: " + path.extname(__filename));
const fileInfo = path.parse(__filename)
console.log(fileInfo);
console.log("root: " + fileInfo['root']);
console.log("dir: " + fileInfo['dir']);
console.log("base: " + fileInfo['base']);
console.log("ext: " + fileInfo['ext']);
console.log("name: " + fileInfo['name']);
console.log('\n');
