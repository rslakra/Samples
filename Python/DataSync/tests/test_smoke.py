import compileall
import unittest
from pathlib import Path


class TestDataSyncSamples(unittest.TestCase):
    def test_src_compiles(self):
        root = Path(__file__).resolve().parents[1]
        src = root / "src"
        self.assertTrue(src.is_dir(), msg=f"Missing {src}")
        self.assertTrue(compileall.compile_dir(str(src), quiet=1))
