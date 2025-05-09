import "./Category.css"
import { useMemo } from 'react';

function getTextColor(bgColor) {
    const tempElem = document.createElement("div");
    tempElem.style.color = bgColor;
    document.body.appendChild(tempElem);
    const computedColor = getComputedStyle(tempElem).color;
    document.body.removeChild(tempElem);

    const rgb = computedColor.match(/\d+/g).map(Number);
    const luminance = (0.299 * rgb[0] + 0.587 * rgb[1] + 0.114 * rgb[2]) / 255;
    return luminance > 0.6 ? 'text-dark' : 'text-white';
}

export default function Category({categoryName, imgUrl, bgColor, numberOfItems,isSelected, onClick}) {
    const textColor = useMemo(() => getTextColor(bgColor), [bgColor]);
    return (
        <div className={`d-flex align-items-center p-3 rounded gap-1 position-relative category-hover ${textColor}`}
        style={{backgroundColor: bgColor , cursor: 'pointer'}}
             onClick={onClick}
        >
            <div style={{position: 'relative', marginRight: '15px'}}>
                <img src={imgUrl} alt={categoryName} className="category-image"/>
            </div>
            <div>
                <h6 className="mb-0">{categoryName}</h6>
                <p className="mb-0">{numberOfItems} Items</p>
            </div>
            {isSelected && <div className={`active-category`}></div>}
        </div>
    )
}