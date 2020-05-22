<template>
    <div>
        <form novalidate class="md-layout md-alignment-top-center" @submit.prevent="login()">
            <md-card class="md-layout-item md-size-30 md-small-size-100">
                <md-card-header>
                    <div class="md-title">Login</div>
                </md-card-header>
                <md-card-content>
                    <div class="md-layout md-gutter">
                    <div class="md-layout-item md-small-size-100">
                        <md-field>
                            <label for="first-name">E-mail</label>
                            <md-input type="email" name="email" id="email" v-model="form.email" autocomplete="email" />
                        </md-field>
                    </div>
                    </div>
                    <div class="md-layout-item md-small-size-100">
                        <md-field>
                            <label for="first-name">Password</label>
                            <md-input type="password" name="password" id="password" v-model="form.password"/>
                        </md-field>
                    </div>
                </md-card-content>
                <md-card-actions>
                    <md-button type="submit" class="md-primary">Login</md-button>
                </md-card-actions>
            </md-card>
        </form>
    </div>
</template>

<script>
export default {
    name: "Login",
    data: function() {
        return {
            form: {
                email: undefined,
                password: undefined
            }
        }
    },
    methods: {
        login: function() {
            alert(this.form.email + ' ' + this.form.password)
        }
    }
}
</script>

<style scoped>
</style>