import axios from "axios";

export default {
	register(user) {
		return axios.post("/account/register/", user).then((response) => {
			return response.data;
		});
	},

	login(user) {
		return axios.post("/account/login/", user).then((response) => {
			if (response.data != null) {
				localStorage.setItem("auth", `Bearer ${response.data.jwt}`);
			}

			return response.data;
		});
	},

	logged() {
		return axios.get("/account/logged/").then((response) => {
			return response.data;
		});
	},
};
