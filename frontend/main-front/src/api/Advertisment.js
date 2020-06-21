import axios from "axios";

export default {
	postAdvertisment(advertisment) {
		return axios.post("/agent/ad/", advertisment).then((response) => {
			return response.data;
		});
	},

	postPhotos(id, photos) {
		const config = {
			headers: {
				"content-type": "multipart/form-data",
			},
		};

		return axios.post(`/agent/images/${id}/`, photos, config).then((response) => {
			return response.data;
		});
	},

	getAllAdvertisements() {
		return axios.get(`/view/`).then((response) => {
			return response.data;
		})
	},

	getDetailedAdvertisement(id) {
		return axios.get(`/view/${id}`).then((response) => {
			return response.data;
		})
	},

	addRentingInterval(rentingInterval) {
		return axios.post("/agent/interval/", rentingInterval).then((response) => {
			return response.data;
		})
	},

	getUserPublishedAdvertisements(ownerId) {
		return axios.get(`/view/agent/${ownerId}`).then((response) => {
			return response.data;
		});
	},

	getStatisticReport(ownerId) {
		return axios.get(`/agent/ad/${ownerId}/statistic`).then((response) => {
			return response.data;
		});
	}
};
