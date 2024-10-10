/** @type {import('tailwindcss').Config} */
module.exports = {
    content: [
        './src/**/*.{html,ts}',
    ],
    theme: {
        extend: {},
    },
    plugins: [
        require('duo-icons/plugin'),
        require('@tailwindcss/typography'),
    ],
};

