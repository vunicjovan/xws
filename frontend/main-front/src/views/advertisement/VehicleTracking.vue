<template>
	<div class="mydiv">
		<div style="height: 800px; width: 100%">
			<l-map ref="map" :zoom="zoom" :center="center" :options="mapOptions" style="height: 80%" @update:center="centerUpdate" @update:zoom="zoomUpdate">
				<l-tile-layer :url="url" :attribution="attribution" />
				<l-marker :lat-lng="currentMark"> </l-marker>
			</l-map>
		</div>
	</div>
</template>

<script>
import L from "leaflet";
import { LMap, LTileLayer, LMarker, LIcon } from "vue2-leaflet";

export default {
	name: "VehicleTracking",
	components: {
		LMap,
		LTileLayer,
		LMarker,
		//LIcon,
	},
	data() {
		return {
			zoom: 15,
			center: L.latLng(45.228601, 20.0198728),
			url: "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
			attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
			currentMark: L.latLng(45.228601, 20.0198728),
			currentZoom: 11.5,
			currentCenter: L.latLng(45.228601, 20.0198728),
			showParagraph: false,
			mapOptions: {
				zoomSnap: 0.5,
			},
			counter: -1,
			index: 0,
			cord: [
				{
					markCenter: L.latLng(45.228601, 20.0198728),
				},
				{
					markCenter: L.latLng(45.2286104, 20.0198789),
				},
				{
					markCenter: L.latLng(45.224655, 20.020871),
				},
				{
					markCenter: L.latLng(45.22235, 20.020281),
				},
				{
					markCenter: L.latLng(45.220279, 20.019702),
				},
			],
		};
	},
	methods: {
		zoomUpdate(zoom) {
			this.currentZoom = zoom;
		},
		centerUpdate(center) {
			this.currentCenter = center;
		},
	},
	watch: {
		counter: {
			handler(value) {
				if (value % 3 == 0) {
					this.$refs.map.setCenter(this.cord[this.index].markCenter);
					this.currentMark = this.cord[this.index].markCenter;
					this.index++;
					if (this.index > 4) {
						this.index = 0;
					}
				}
				setTimeout(() => {
					this.counter++;
					if (this.counter == 9) {
						this.counter = 0;
					}
				}, 1000);
			},
			immediate: true,
		},
	},
};
</script>

<style scoped>
.mydiv {
	margin: 2.5%;
}
.someExtraClass {
	background-color: aqua;
	padding: 10px;
	border: 1px solid #333;
	border-radius: 0 20px 20px 20px;
	box-shadow: 5px 3px 10px rgba(0, 0, 0, 0.2);
	text-align: center;
	width: auto !important;
	height: auto !important;
	margin: 0 !important;
}
</style>
