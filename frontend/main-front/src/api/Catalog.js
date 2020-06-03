import axios from "axios";

export default {
	async getCatalog() {
		const response = await axios.get("/catalog/");
		return response.data;
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
	},

	// BRAND METHODS
	async addBrand(brand) {
		const response = await axios.post(`/catalog/brand/`, brand);
		return response.data;
	},

	async updateBrand(brand) {
		const response = await axios.put(`/catalog/brand/${brand.id}`, brand);
		return response.data;
	},

	async deleteBrand(id) {
		const response = await axios.delete(`/catalog/brand/${id}`);
		return response.data;
	},

	// VEHICLE_CLASS METHODS
	async deleteVehicleClass(id) {
		try {
			const response = await axios.delete(`/catalog/vehicleClass/${id}`);
			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},

	async addVehicleClass(vehicleClass) {
		try {
			const response = await axios.post("/catalog/vehicleClass/", vehicleClass);
			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},

	async updateVehicleClass(vehicleClass) {
		try {
			const response = await axios.put(`/catalog/vehicleClass/${vehicleClass.id}`, vehicleClass);
			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},

};
