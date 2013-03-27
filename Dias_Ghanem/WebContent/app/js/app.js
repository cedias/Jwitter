

var env = new Environnement("disconnected");

var resp = '{"id":2,"login":"marwan","key":"421l073hpv1bvUQi0k41U064v6nxo0rH"}';

var result = JSON.parse(resp);
var user = new User(result.login,result.id,result.key);

console.log(user);