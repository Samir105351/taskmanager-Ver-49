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