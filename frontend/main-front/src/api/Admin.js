import axios from "axios"

export default {
    blockUser(userId) {
		return axios.put(`/account/block/${userId}`).then((response) => {
			return response.data;
		});
    },
    
    deleteUser(userId) {
		return axios.delete(`/account/${userId}`).then((response) => {
			return response.data;
		});
    },
    
    getSimpleUsers() {
		return axios.get("/account/").then((response) => {
			return response.data;
		});
	},

	getUnregisteredAgents() {
		return axios.get("/account/unregistered/").then((response) => {
			return response.data;
		});
	},

	sendRegistrationResponse(registrationResponse) {
		return axios.put("/account/registerAgent/", registrationResponse).then((response) => {
			return response.data;
		});
	}
}
