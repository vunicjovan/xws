<template>
	<div>
		<transition name="fade">
			<span v-if="show" class="md-layout md-alignment-top-center md-headline">Welcome to RentaSoul service</span>
		</transition>
	</div>
</template>

<script>
export default {
	name: "HelloWorld",
	props: {
		msg: String,
	},
	data: function() {
		return {
			show: false,
		};
	},
	mounted: function() {
		this.fadeMe();
	},
	methods: {
		fadeMe: function() {
			this.show = !this.show;
		},
	},
};
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
	transition: opacity 2.5s;
}

.fade-enter,
.fade-leave-to {
	opacity: 0;
}
</style>
