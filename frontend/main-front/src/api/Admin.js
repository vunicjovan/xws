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
		return axios.post("/account/users/").then((response) => {
			return response.data;
		});
	},
}
