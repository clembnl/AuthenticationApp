<template>
    <button @click='onClick' class='btn'>Reset Password</button>
</template>

<script>
export default {
    name: 'ResetButton',
    methods: {
        onClick(e) {
            e.preventDefault()

            this.$emit('reset')
        }
    }
}
</script>

<style scoped>
.btn {
  margin-top: 20px;
  background-color: rgb(120, 120, 120);
  width: 17%;
  height: 25px;
  font-size: 0.9em;
  font-weight: 400;
  color: white;
}
</style>