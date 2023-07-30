    function copyToClipboard(text, tooltip) {
        const textarea = document.createElement('textarea');
        textarea.value = text;
        document.body.appendChild(textarea);
        textarea.select();
        document.execCommand('copy');
        document.body.removeChild(textarea);
        showTooltip(tooltip);
    }

    function showTooltip(tooltip) {
        tooltip.classList.add('show');
        setTimeout(() => {
            tooltip.classList.remove('show');
        }, 1500); // Show tooltip for 1.5 seconds
    }

    function copyListToClipboard1(listText) {
        copyToClipboard(listText1, document.querySelector('#tooltip-list1'));
    }
        function copyListToClipboard2(listText) {
            copyToClipboard(listText2, document.querySelector('#tooltip-list2'));
        }

    function copyPreToClipboard(preId) {
        const preText = document.querySelector("#" + preId).innerText;
        copyToClipboard(preText, document.querySelector("#tooltip-list3"));
    }