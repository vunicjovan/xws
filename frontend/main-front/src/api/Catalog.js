import axios from "axios";

export default {
	getCatalog() {
		return axios.get("/catalog/").then((response) => {
			return response.data;
		});
	},

	// FUEL_TYPE METHODS
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
	},

	// GEARBOX_TYPE METHODS
	async deleteGearboxType(id) {
		const response = await axios.delete(`/catalog/gearboxType/${id}`);
		return response.data;
	},

	async addGearboxType(gearboxType) {
		const response = await axios.post(`/catalog/gearboxType/`, gearboxType);
		return response.data;
	},

	async updateGearboxType(gearboxType) {
		const response = await axios.put(`/catalog/gearboxType/${gearboxType.id}`, gearboxType);
		return response.data;
	}
};
