    function redirectToSelectedLink(selectElement) {
        const selectedOption = selectElement.value;
        if (selectedOption) {
            window.location.href = selectedOption;
        }
    }