import unittest
from pathlib import Path
import runpy


class TestJWTUtilsModule(unittest.TestCase):
    def test_jwt_utils_module_loads(self):
        root = Path(__file__).resolve().parents[1]
        path = root / "src" / "utils" / "JWTUtils.py"
        self.assertTrue(path.is_file(), msg=f"Missing {path}")
        ns = runpy.run_path(str(path))
        self.assertIn("createJWTToken", ns)
