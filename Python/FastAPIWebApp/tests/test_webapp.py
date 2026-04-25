#
# Author: Rohtash Lakra
#
from unittest import TestCase

from fastapi.testclient import TestClient

from tests import app


class WebAppTest(TestCase):

    def setUp(self):
        """The setUp() method of the TestCase class is automatically invoked before each test, so it's an ideal place
        to insert common logic that applies to all the tests in the class"""
        print("+setUp()")
        self.client = TestClient(app)
        print("-setUp()")
        print()

    def tearDown(self):
        """The tearDown() method of the TestCase class is automatically invoked after each test, so it's an ideal place
        to insert common logic that applies to all the tests in the class"""
        print("+tearDown()")
        self.client = None
        print("-tearDown()")
        print()

    def test_webapp(self):
        """Tests the WebApp object"""
        print("+test_webapp()")
        response = self.client.get("/openapi.json")

        # valid object and expected results
        self.assertEqual(response.status_code, 200)
        self.assertIn("openapi", response.json())
        print("-test_webapp()")
        print()
