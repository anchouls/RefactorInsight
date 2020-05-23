package data.types.attributes;

import data.RefactoringInfo;
import data.Scope;
import data.TrueCodeRange;
import data.types.Handler;
import gr.uom.java.xmi.diff.ExtractAttributeRefactoring;
import java.util.Arrays;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringType;

public class ExtractAttributeHandler implements Handler {

  @Override
  public RefactoringInfo handle(Refactoring refactoring) {
    ExtractAttributeRefactoring ref = (ExtractAttributeRefactoring) refactoring;
    return new RefactoringInfo(Scope.ATTRIBUTE)
        .setType(RefactoringType.EXTRACT_ATTRIBUTE)
        .setName(ref.getName())
        .setText(ref.toString())
        .setNameBefore(ref.getOriginalClass().getName())
        .setNameAfter(ref.getNextClass().getName())
        .setLeftSide(Arrays.asList(new TrueCodeRange(ref.getVariableDeclaration().codeRange())))
        .setRightSide(
            Arrays.asList(new TrueCodeRange(ref.getExtractedVariableDeclarationCodeRange())));
  }
}
