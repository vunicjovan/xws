<template>
    <div class="somediv">
        <div class="md-headline">My debts</div>
        <md-list class="md-triple-line md-dense" v-for="debt in debts" v-bind:key="debt.id">
            <md-list-item>
                <md-avatar>
                    <i class="fa fa-user-circle fa-2x" aria-hidden="true"></i>
                </md-avatar>

                <div class="md-list-item-text">
                    <span class="ownerc">{{ debt.value }} €</span>
                </div>
                
                <span class="some-span">
                    <md-button class="md-icon-button md-list-action">
                        <i class="fas fa-check-circle fa-2x"></i>
                        <md-tooltip>Pay</md-tooltip>
                    </md-button>
                </span>
            </md-list-item>
            <md-divider class="md-inset"></md-divider>
        </md-list>
    </div>
</template>

<script>
export default {
    name: "Debts",
    data: function() {
        return {
            "debts": [
                {
                    "id": 1,
                    "value": 1000
                },
                {
                    "id": 2,
                    "value": 2000
                },
                {
                    "id": 3,
                    "value": 3000
                },
            ]
        };
    }
}
</script>

<style>

</style>
