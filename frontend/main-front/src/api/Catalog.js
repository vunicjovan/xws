import axios from "axios";

export default {
	getCatalog() {
		return axios.get("/catalog/").then((response) => {
			return response.data;
		});
	},

	async deleteFuelType(id) {
		const response = await axios.delete(`/catalog/fuelType/${id}`);
		return response.data;
	},

	async addFuelType(fuelType) {
		const response = await axios.post(`/catalog/fuelType/`, fuelType);
		return response.data;
	},

	async updateFuelType(fuelType) {
		const response = await axios.put(`/catalog/fuelType/${fuelType.id}`, fuelType);
		return response.data;
	}
};
