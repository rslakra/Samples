#
# Author: Rohtash Lakra
# References:
#  - https://blog.mcpolemic.com/2016/01/18/adding-request-ids-to-flask.html
import logging

import flask

"""
Request IDs

A request ID is a unique identifier for each request. We decided that we wanted to be able to search for both a unique 
action and an entire request chain, so we implemented the following model:

- If a request comes in with no request ID, generate a unique one.
- If a request comes in with a request ID, add a comma and add a unique one.

This allows the requests chain (which could have any number of requests in between them) to  handle easily.

"""

import uuid
from contextvars import ContextVar

request_id: ContextVar[uuid.UUID] = ContextVar('request_id', default=uuid.UUID('00000000-0000-0000-0000-000000000000'))


class RequestIdFilter(logging.Filter):
    """Provides request id parameter for the logger"""

    # This is a logging filter that makes the request ID available for use in
    # the logging format. Note that we're checking if we're in a request
    # context, as we may want to log things before Flask is fully loaded.
    def filter(self, record):
        record.request_id = self._request_id() if flask.has_request_context() else ''
        # record.request_id = request_id.get()
        return True

    def _generate_request_id(self, original_request_id=''):
        """Generate a new request ID, optionally including an original request ID"""
        # request_id = uuid.uuid4()
        request_id = uuid.uuid4().hex

        if original_request_id:
            request_id = "{}, {}".format(original_request_id, request_id)

        return request_id

    # Returns the current request ID or a new one if there is none
    def _request_id(self):
        """
        Returns the current request ID or a new one if there is none
        A request ID is a unique identifier for each request. We decided that we wanted to be able to search for both a unique
        action and an entire request chain, so we implemented the following model:

        - If a request comes in with no request ID, generate a unique one.
        - If a request comes in with a request ID, add a comma and add a unique one.
        """
        if getattr(flask.g, 'request_id', None):
            return flask.g.request_id

        # if request doesn't contain request_id, generate new for it
        headers = flask.request.headers

        # read request-id header
        original_request_id = headers.get("X-Request-Id")
        request_id = self._generate_request_id(original_request_id)
        flask.g.request_id = request_id

        return request_id

    def print_reqeust_id(self):
        """Prints request id"""
        print()
        requestId = self._generate_request_id()
        print(f"requestId: {requestId}")
        print(f"Next requestId: {self._generate_request_id(requestId)}")
        print(f"Last requestId: {self._generate_request_id(requestId)}")
        print()


# Uncomment to test it.
# reqeustIdFilter = RequestIdFilter()
# reqeustIdFilter.print_reqeust_id()


def setup_logging():
    # Create a logger
    logger = logging.getLogger(__name__)
    logger.setLevel(logging.DEBUG)

    # Create a handler
    handler = logging.StreamHandler()
    handler.setLevel(logging.DEBUG)

    # Create a formatter with request_id
    formatter = logging.Formatter('%(asctime)s - %(name) - %(levelname)s - %(request_id)s - %(message)s')
    handler.setFormatter(formatter)

    # Add the handler and the filter to the logger
    logger.addHandler(handler)
    logger.addFilter(RequestIdFilter())

    return logger
