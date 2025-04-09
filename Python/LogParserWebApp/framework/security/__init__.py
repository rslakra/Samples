#
# Author: Rohtash Lakra
#
from fastapi import Header, HTTPException, status


def validate_headers(
        user_agent: str = Header(..., alias="User-Agent"),
        x_api_key: str = Header(..., alias="X-API-KEY"),
) -> dict:
    """
    Validates that the `User-Agent` and `X-API-KEY` are present in header.
    Args:
        user_agent (str): The `User-Agent` header value.
        x_api_key (str): The `x-api-key` header value.
    Returns:
        dict: A dictionary with the validated headers.
    Raises:
        HTTPException: If headers are missing or invalid.
    """
    if not user_agent:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="Header 'User-Agent' is required!",
        )
    if not x_api_key:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="Header 'X-API-KEY' is required!",
        )

    # TODO: Secure API key validation (optional)
    # validate the API key against a list of valid keys
    # if x_api_key != app_settings.API_KEY:
    #     raise HTTPException(
    #         status_code=status.HTTP_401_UNAUTHORIZED,
    #         detail="Invalid API key.",
    #     )
    return {"User-Agent": user_agent, "x-api-key": x_api_key}
