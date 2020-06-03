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
		return axios.post(`/view/`).then((response) => {
			return response.data
		})
	},

	getDetailedAdvertisement(id) {
		return axios.post(`/view/${id}`).then((response) => {
			return response.data
		})
	},
};
