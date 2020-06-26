const fs = require("fs");

module.exports = {
	devServer: {
		port: 8090,
		https: {
			cert: fs.readFileSync("./src/client.crt"),
			key: fs.readFileSync("./src/client.key"),
		},
	},
};
