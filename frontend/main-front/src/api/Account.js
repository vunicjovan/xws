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

	changePassword(passwordSet) {
		return axios.put("/account/changePassword/", passwordSet).then((response) => {
			return response.data;
		});
	},

	activateAccount(token) {
		return axios.put(`/account/activate/${token}`).then((response) => {
			return response.data;
		});
	},

	createResetToken(email) {
		return axios.post("/account/invokeReset/", email).then((response) => {
			return response.data;
		});
	},

	resetPassword(tokenDTO) {
		return axios.put("/account/resetPassword/", tokenDTO).then((response) => {
			return response.data;
		});
	},

	updateUser(updateDTO) {
		return axios.put(`/account/${updateDTO.id}`, updateDTO).then((response) => {
			return response.data;
		});
	},
	
};
