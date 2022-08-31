try {
    var a;
} catch (error) {
    // nothing to do.
}

async function f() {
    try {
        await Promise.reject("async error")
    } catch (err) {
        console.log('error caught in async function')
    }

    try {
        throw new Error('error thrown')
    } catch (err) {
        console.log('second error caught')
    }
} 

f().then(() => {}).catch(() => {})