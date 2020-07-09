import axios from "axios";

export default {
	async getDebt(userId) {
		try {
			const response = await axios.get(`/rent/debt/user/${userId}`);
			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},

	async getIncome(agentId) {
		try {
			const response = await axios.get(`/rent/debt/agent/${agentId}`);
			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},

	async payDebt(id) {
		try {
			const response = await axios.delete(`/rent/debt/${id}`);
			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},
};
