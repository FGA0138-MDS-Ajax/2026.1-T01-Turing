type BrandLogoProps = {
  compact?: boolean
  light?: boolean
}

export function BrandMark({ light = false }: { light?: boolean }) {
  const blue = light ? 'currentColor' : '#003a7a'
  const green = light ? 'currentColor' : '#00822e'

  return (
    <svg viewBox="0 0 512 512" role="img" aria-label="Seu espaço UnB" className="brand-mark">
      <path
        d="M40 246.4v165.6h198a96 96 0 0 0-73.6-94.1A228 120 0 0 1 40 246.4Z"
        fill={green}
      />
      <path
        d="M472 246.4a228 120 0 0 1-124.4 71.5A96 96 0 0 0 274 412h198V246.4Z"
        fill={green}
      />
      <path
        d="M256 100A216 108 0 0 0 40 208a216 108 0 0 0 131.3 99.4A108 108 0 0 1 250 411.3v.7h12v-.6a108 108 0 0 1 81.8-104.7A216 108 0 0 0 472 208 216 108 0 0 0 256 100Zm0 36a72 72 0 1 1 0 144 72 72 0 0 1 0-144Z"
        fill={blue}
      />
      <path d="M256 172a36 36 0 1 0 0 72 36 36 0 0 0 0-72Z" fill={blue} />
    </svg>
  )
}

export default function BrandLogo({ compact = false, light = false }: BrandLogoProps) {
  return (
    <span className={`brand-logo ${light ? 'brand-logo--light' : ''}`}>
      <BrandMark light={light} />
      {!compact && (
        <span className="brand-wordmark">
          Seu espaço <strong>UnB</strong>
        </span>
      )}
    </span>
  )
}
