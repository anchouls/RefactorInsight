package data.types.classes;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.LocalFilePath;
import com.intellij.openapi.vcs.VcsException;
import data.Group;
import data.RefactoringInfo;
import data.RefactoringLine;
import data.types.Handler;
import git4idea.GitContentRevision;
import git4idea.GitRevisionNumber;
import gr.uom.java.xmi.diff.ExtractClassRefactoring;
import org.refactoringminer.api.Refactoring;

public class ExtractClassHandler extends Handler {

  @Override
  public RefactoringInfo specify(Refactoring refactoring, RefactoringInfo info, Project project) {
    ExtractClassRefactoring ref = (ExtractClassRefactoring) refactoring;

    if (ref.getExtractedClass().isInterface()) {
      info.setGroup(Group.INTERFACE);
    } else if (ref.getExtractedClass().isAbstract()) {
      info.setGroup(Group.ABSTRACT);
    } else {
      info.setGroup(Group.CLASS);
    }

    info
        .setDetailsBefore(ref.getOriginalClass().getPackageName())
        .setDetailsAfter(ref.getExtractedClass().getPackageName())
        .setElementBefore(ref.getOriginalClass().getName())
        .setElementAfter(ref.getExtractedClass().getName())
        .setNameBefore(ref.getExtractedClass().getName())
        .setNameAfter(ref.getExtractedClass().getName())
        .setThreeSided(true);

    if (ref.getAttributeOfExtractedClassTypeInOriginalClass() != null) {
      ref.getExtractedOperations().forEach(operation -> {
        info.addMarking(operation.codeRange(), ref.getExtractedClass().codeRange(),
            ref.getAttributeOfExtractedClassTypeInOriginalClass().codeRange(),
            RefactoringLine.VisualisationType.LEFT);
      });

      ref.getExtractedAttributes().forEach(operation -> {
        info.addMarking(operation.codeRange(), ref.getExtractedClass().codeRange(),
            ref.getAttributeOfExtractedClassTypeInOriginalClass().codeRange(),
            RefactoringLine.VisualisationType.LEFT);
      });

      info.addMarking(1, 1, ref.getExtractedClass().codeRange().getStartLine(),
          ref.getExtractedClass().codeRange().getStartLine(),
          ref.getAttributeOfExtractedClassTypeInOriginalClass().codeRange().getStartLine(),
          ref.getAttributeOfExtractedClassTypeInOriginalClass().codeRange().getEndLine(),
          ref.getOriginalClass().codeRange().getFilePath(),
          ref.getExtractedClass().codeRange().getFilePath(),
          ref.getAttributeOfExtractedClassTypeInOriginalClass().codeRange().getFilePath(),
          RefactoringLine.VisualisationType.RIGHT,
          refactoringLine -> {
            int[] midColumns = new int[] {1, 1};
            try {
              String path = project.getBasePath() + "/"
                  + ref.getExtractedClass().codeRange().getFilePath();
              String midText = GitContentRevision.createRevision(
                  new LocalFilePath(path, false),
                  new GitRevisionNumber(info.getCommitId()), project).getContent();
              String[] nameSpace = ref.getExtractedClass().getName().split("\\.");
              String className = nameSpace[nameSpace.length - 1];
              midColumns = findColumns(midText, className,
                  ref.getExtractedClass().codeRange().getStartLine());
            } catch (VcsException e) {
              e.printStackTrace();
            }

            refactoringLine.setColumns(new int[] {1, 1, midColumns[0], midColumns[1],
                ref.getAttributeOfExtractedClassTypeInOriginalClass()
                    .codeRange().getStartColumn(),
                ref.getAttributeOfExtractedClassTypeInOriginalClass()
                    .codeRange().getEndColumn()});
          });
    }
    return info;
  }
}
