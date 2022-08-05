import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { WorkEffortAssocDetailComponent } from 'app/entities/work-effort-assoc/work-effort-assoc-detail.component';
import { WorkEffortAssoc } from 'app/shared/model/work-effort-assoc.model';

describe('Component Tests', () => {
  describe('WorkEffortAssoc Management Detail Component', () => {
    let comp: WorkEffortAssocDetailComponent;
    let fixture: ComponentFixture<WorkEffortAssocDetailComponent>;
    const route = ({ data: of({ workEffortAssoc: new WorkEffortAssoc(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortAssocDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WorkEffortAssocDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorkEffortAssocDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load workEffortAssoc on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.workEffortAssoc).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
